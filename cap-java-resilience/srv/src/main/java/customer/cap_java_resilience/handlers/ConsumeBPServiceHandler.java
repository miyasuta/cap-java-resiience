package customer.cap_java_resilience.handlers;

import java.time.Duration;

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
    @Autowired
    @Qualifier(BusinessPartnerService_.CDS_NAME)
    CqnService bupa;

    @On(event = CqnService.EVENT_READ, entity = LocalBusinessPartners_.CDS_NAME)
    public Result onReadBusinessPartners(CdsReadEventContext context) {
        // Create Resilience Configuration with Cache settings
        ResilienceConfiguration resilienceConfig = ResilienceConfiguration.of(BusinessPartnerService.class);
        
        ResilienceConfiguration.CacheConfiguration cacheConfig = ResilienceConfiguration.CacheConfiguration
            .of(Duration.ofSeconds(30)).withoutParameters();
        resilienceConfig.cacheConfiguration(cacheConfig);

        // Execute the service call within cache configuration
        // return ResilienceDecorator.executeSupplier(() -> bupa.run(context.getCqn()), resilienceConfig);

        // Use separate method for business partner service call
        return ResilienceDecorator.executeSupplier(() -> callBusinessPartnerService(context), resilienceConfig);
    }

    private Result callBusinessPartnerService(CdsReadEventContext context) {
        return bupa.run(context.getCqn());
    }
}