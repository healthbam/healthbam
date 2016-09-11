package org.hmhb.config;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsConfig.class);

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        Slf4jReporter slf4jReporter = Slf4jReporter.forRegistry(metricRegistry)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .convertRatesTo(TimeUnit.MILLISECONDS)
                .outputTo(LOGGER)
                .build();
        registerReporter(slf4jReporter).start(10, TimeUnit.MINUTES);
    }

}
