using { BusinessPartnerService as external } from './external/BusinessPartnerService.cds';

service ConsumeBPService {
    entity BusinessPartners as projection on external.BusinessPartners;
}