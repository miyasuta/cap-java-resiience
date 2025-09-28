using { bp as db } from '../db/schema';

service BusinessPartnerService {
    entity BusinessPartners as projection on db.BusinessPartners
}