package org.hmhb.authentication;

import javax.annotation.Nonnull;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.annotation.Timed;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hmhb.config.ConfigService;
import org.hmhb.exception.authentication.AuthHeaderHasTooManyPartsException;
import org.hmhb.exception.authentication.AuthHeaderMissingTokenException;
import org.hmhb.exception.authentication.AuthHeaderUnknownException;
import org.hmhb.exception.authentication.JwtTokenExpiredException;
import org.hmhb.exception.authentication.JwtTokenNotActiveYetException;
import org.hmhb.exception.authentication.JwtTokenNotSignedException;
import org.hmhb.user.HmhbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link JwtAuthenticationService}.
 */
@Service
public class DefaultJwtAuthenticationService implements JwtAuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationService.class);

    private final ConfigService configService;

    /**
     * An injectable constructor.
     *
     * @param configService the {@link ConfigService} to get config from
     */
    @Autowired
    public DefaultJwtAuthenticationService(
            @Nonnull ConfigService configService
    ) {
        this.configService = requireNonNull(configService, "configService cannot be null");
    }

    private String getDomain() {
        return configService.getPrivateConfig().getJwtDomain();
    }

    private String getSecret() {
        return configService.getPrivateConfig().getJwtSecret();
    }

    private Date getTokenExpirationDate() {
        /* one week */
        return new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
    }

    @Timed
    @Override
    public String generateJwtToken(
            @Nonnull HmhbUser user
    ) {
        LOGGER.debug("generateJwtToken called: user={}", user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("admin", user.isAdmin());
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        claims.put("displayName", user.getDisplayName());
        claims.put("imageUrl", user.getImageUrl());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        String domainId = getDomain();
        String secret = getSecret();

        String token = Jwts.builder()
                /* you must set claims before the others or else setClaims will clear your other properties */
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(getTokenExpirationDate())
                .setSubject("" + user.getId())
                .setAudience(domainId)
                .setIssuer(domainId)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        LOGGER.debug("token: {}", token);

        return token;
    }

    @Timed
    @Override
    public void validateAuthentication(
            @Nonnull HttpServletRequest request
    ) {
        LOGGER.debug("validateAuthentication called");
        requireNonNull(request, "request cannot be null");

        String authHeader = request.getHeader("Authorization");

        LOGGER.debug("processing authentication: header={}", authHeader);

        if (authHeader != null) {

            String[] authHeaderInfo = authHeader.split("[ ]");

            if (authHeaderInfo.length < 2) {
                throw new AuthHeaderMissingTokenException(authHeader);
            }

            if (authHeaderInfo.length > 2) {
                throw new AuthHeaderHasTooManyPartsException(authHeader);
            }

            if (!"Bearer".equals(authHeaderInfo[0])) {
                throw new AuthHeaderUnknownException(authHeader);
            }

            String authToken = authHeaderInfo[1];

            validateToken(request, authToken);
        }
    }

    @Timed
    @Override
    public void validateToken(
            @Nonnull HttpServletRequest request,
            @Nonnull String authToken
    ) {
        LOGGER.debug("validateToken called");
        requireNonNull(request, "request cannot be null");
        requireNonNull(authToken, "authToken cannot be null");

        if (!Jwts.parser().isSigned(authToken)) {
            throw new JwtTokenNotSignedException();
        }

        String domainId = getDomain();
        String secret = getSecret();

        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secret);
        /* I'm using setSigningKey with the SecretKeySpec so I can enforce which algorithm to use. */
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        LOGGER.debug("authToken is: {}", authToken);

        Claims claims = Jwts.parser()
                .requireAudience(domainId)
                .requireIssuer(domainId)
                .setSigningKey(secretKey)
                .parseClaimsJws(authToken)
                .getBody();

        String username = claims.getSubject();
        Date issuedAt = claims.getIssuedAt();
        Date expirationDate = claims.getExpiration();
        String issuer = claims.getIssuer();
        String audience = claims.getAudience();
        boolean admin = claims.get("admin", Boolean.class);
        String email = claims.get("email", String.class);
        Long userId = claims.get("userId", Integer.class).longValue();
        String displayName = claims.get("displayName", String.class);
        String imageUrl = claims.get("imageUrl", String.class);
        String firstName = claims.get("firstName", String.class);
        String lastName = claims.get("lastName", String.class);

        Date now = new Date();

        if (issuedAt.after(now)) {
            throw new JwtTokenNotActiveYetException(issuedAt);
        }

        if (expirationDate.before(now)) {
            throw new JwtTokenExpiredException(expirationDate);
        }

        LOGGER.debug(
                "token accepted, standard info: issuer={}, audience={}, username={}, issuedAt={}, expiresOn={}",
                issuer,
                audience,
                username,
                issuedAt,
                expirationDate
        );

        LOGGER.debug(
                "token accepted, custom info: admin={}, email={}, userId={}",
                admin,
                email,
                userId
        );

        HmhbUser user = new HmhbUser();
        user.setAdmin(admin);
        user.setEmail(email);
        user.setId(userId);
        user.setDisplayName(displayName);
        user.setImageUrl(imageUrl);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        request.setAttribute("loggedInUser", user);
    }

}
