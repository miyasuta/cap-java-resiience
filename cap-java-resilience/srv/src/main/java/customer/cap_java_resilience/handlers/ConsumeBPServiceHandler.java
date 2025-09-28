package customer.cap_java_resilience.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.Result;
import com.sap.cds.services.cds.CdsReadEventContext;

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
        return bupa.run(context.getCqn());
    }
}