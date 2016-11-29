package org.hmhb;

import javax.annotation.Nonnull;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.config.ConfigService;
import org.hmhb.config.PublicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import static java.util.Objects.requireNonNull;

/**
 * Spring MVC {@link Controller} for serving our our single page app (index.html).
 */
@Controller
public class ViewsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewsController.class);

    private static final String INDEX_HTML_PATH = "index";
    private static final String GOOGLE_MAP_CLIENT_ID_ATTR = "googleMapsApiKey";

    private final PublicConfig publicConfig;

    /**
     * An injectable constructor.
     *
     * @param configService the {@link ConfigService} for config
     */
    @Autowired
    public ViewsController(
            @Nonnull ConfigService configService
    ) {
        LOGGER.debug("constructed");
        requireNonNull(configService, "configService cannot be null");
        this.publicConfig = configService.getPublicConfig();
    }

    private String handleSinglePageApp(
            @Nonnull Model model
    ) {
        requireNonNull(model, "model cannot be null");
        model.addAttribute(GOOGLE_MAP_CLIENT_ID_ATTR, publicConfig.getGoogleGeocodeClientId());
        return INDEX_HTML_PATH;
    }

    /**
     * Serves our single page app for any sub-views.
     *
     * @return our single page app's index.html
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/views/**"
    )
    public String handleViewsSubpaths(
            @Nonnull Model model
    ) {
        LOGGER.debug(
                "handleViewsSubpaths called: {}",
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE
        );
        return handleSinglePageApp(model);
    }

    /**
     * Serves our single page app for the top level view.
     *
     * @return our single page app's index.html
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/views"
    )
    public String handleViews(
            @Nonnull Model model
    ) {
        LOGGER.debug("handleViews called");
        return handleSinglePageApp(model);
    }

    /**
     * Serves our single page app for the server root (making index.html the
     * default page).
     *
     * @return our single page app's index.html
     */
    @Timed
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/"
    )
    public String handleRoot(
            @Nonnull Model model
    ) {
        LOGGER.debug("handleRoot called");
        return handleSinglePageApp(model);
    }

}
