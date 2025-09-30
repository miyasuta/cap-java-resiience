using { bp as db } from '../db/schema';

service BusinessPartnerService {
    entity BusinessPartners as projection on db.BusinessPartners;

    action loadBusinessPartners(number: Integer) returns String;
}