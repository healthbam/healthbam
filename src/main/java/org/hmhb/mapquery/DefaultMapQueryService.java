package org.hmhb.mapquery;

import javax.annotation.Nonnull;

import java.util.List;
import java.util.stream.Collectors;

import com.codahale.metrics.annotation.Timed;
import org.hmhb.program.Program;
import org.hmhb.program.ProgramService;
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

    private final ProgramService programService;

    @Autowired
    public DefaultMapQueryService(
            @Nonnull ProgramService programService
    ) {
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

        String commaSeparatedProgramIds = programs.stream()
                .map(Program::getId)
                .map(Object::toString)
                .collect(Collectors.joining(","));

        MapQueryResult result = new MapQueryResult();
        result.setPrograms(programs);
        result.setMapLayerUrl(
                "https://hmhb.herokuapp.com/api/kml"
                        + "?programIds=" + commaSeparatedProgramIds
                        + "&time=" + System.currentTimeMillis()
        );
        mapQuery.setResult(result);
        return mapQuery;
    }

}
