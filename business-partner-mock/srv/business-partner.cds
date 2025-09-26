using { BusinessPartnerA2X as bupa} from './external/BusinessPartnerA2X';

service BusinessPartnerService {
    entity BusinessPartners as projection on bupa.A_BusinessPartner {
        key BusinessPartner,
        BusinessPartnerCategory,
        BusinessPartnerFullName        
    }
}