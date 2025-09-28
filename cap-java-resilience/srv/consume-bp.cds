using { BusinessPartnerService as external } from './external/BusinessPartnerService.cds';

service ConsumeBPService {
    entity LocalBusinessPartners as projection on external.BusinessPartners;
}