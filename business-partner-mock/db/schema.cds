namespace bp;

entity BusinessPartners {
    key BusinessPartner         : String(10);
        BusinessPartnerCategory : String(1);
        BusinessPartnerFullName : String(81);
}
