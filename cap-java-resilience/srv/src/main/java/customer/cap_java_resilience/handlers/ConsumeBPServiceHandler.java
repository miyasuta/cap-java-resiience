package customer.cap_java_resilience.handlers;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceConfiguration;
import com.sap.cloud.sdk.cloudplatform.resilience.ResilienceDecorator;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.Result;
import com.sap.cds.services.cds.CdsReadEventContext;

import cds.gen.businesspartnerservice.BusinessPartnerService;
import cds.gen.businesspartnerservice.BusinessPartnerService_;
import cds.gen.consumebpservice.ConsumeBPService_;
import cds.gen.consumebpservice.LocalBusinessPartners_;

@Component
@ServiceName(ConsumeBPService_.CDS_NAME)
public class ConsumeBPServiceHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConsumeBPServiceHandler.class);

    @Autowired
    @Qualifier(BusinessPartnerService_.CDS_NAME)
    CqnService bupa;

    @On(event = CqnService.EVENT_READ, entity = LocalBusinessPartners_.CDS_NAME)
    public Result onReadBusinessPartners(CdsReadEventContext context) {
        Instant startTime = Instant.now();
        String queryKey = context.getCqn().toString();

        logger.info("Starting request to BusinessPartnerService");

        // Create Resilience Configuration with Cache settings
        ResilienceConfiguration resilienceConfig = ResilienceConfiguration.of(BusinessPartnerService.class);

        // Include query parameters in cache key by using the query string as parameter
        ResilienceConfiguration.CacheConfiguration cacheConfig = ResilienceConfiguration.CacheConfiguration
            .of(Duration.ofSeconds(30))
            .withParameters(queryKey);
        resilienceConfig.cacheConfiguration(cacheConfig);

        // Execute the service call within cache configuration
        Result result = ResilienceDecorator.executeSupplier(() -> callBusinessPartnerService(context), resilienceConfig);

        long totalTimeMs = Duration.between(startTime, Instant.now()).toMillis();

        // Log performance metrics
        logger.info("Request completed - ExecutionTime: {}ms", totalTimeMs);

        return result;
    }

    private Result callBusinessPartnerService(CdsReadEventContext context) {
        return bupa.run(context.getCqn());
    }
}