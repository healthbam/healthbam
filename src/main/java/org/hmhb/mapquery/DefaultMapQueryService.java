package org.hmhb.mapquery;

import javax.annotation.Nonnull;

import java.util.List;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.county.County;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramService;
import org.hmhb.url.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link MapQueryService}.
 */
@Service
public class DefaultMapQueryService implements MapQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMapQueryService.class);

    private final UrlService urlService;
    private final ProgramService programService;

    @Autowired
    public DefaultMapQueryService(
            @Nonnull UrlService urlService,
            @Nonnull ProgramService programService
    ) {
        this.urlService = requireNonNull(urlService, "urlService cannot be null");
        this.programService = requireNonNull(programService, "programService cannot be null");
    }

    @Timed
    @Override
    public MapQuery search(
            @Nonnull MapQuery mapQuery
    ) {
        LOGGER.debug("search called: mapQuery={}", mapQuery);
        requireNonNull(mapQuery, "mapQuery cannot be null");

        List<Program> programs = programService.search(mapQuery);

        String countyId = "";
        County county = null;

        if (mapQuery.getSearch() != null && mapQuery.getSearch().getCounty() != null) {
            county = mapQuery.getSearch().getCounty();
        }

        if (county != null && county.getId() != null) {
            countyId = county.getId().toString();
        }

        StringBuilder programIdsBuilder = new StringBuilder();

        for (Program program : programs) {
            programIdsBuilder.append(program.getId());
            programIdsBuilder.append(',');
        }

        String commaSeparatedProgramIds = programIdsBuilder.toString();

        if (!commaSeparatedProgramIds.isEmpty()) {
            commaSeparatedProgramIds = commaSeparatedProgramIds.substring(0, commaSeparatedProgramIds.length() - 1);
        }

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);
        result.setMapLayerUrl(
                urlService.getUrlPrefix() + "/api/kml"
                        + "?countyId=" + countyId
                        + "&programIds=" + commaSeparatedProgramIds
                        + "&time=" + System.currentTimeMillis()
        );
        mapQuery.setResult(result);
        return mapQuery;
    }

}
