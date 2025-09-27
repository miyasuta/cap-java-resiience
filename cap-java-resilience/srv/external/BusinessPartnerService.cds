/* checksum : d182bb38fa631a935e2dba02627f7365 */
@cds.external : true
service BusinessPartnerService {
  @cds.external : true
  @cds.persistence.skip : true
  entity BusinessPartners {
    key BusinessPartner : String(10) not null;
    BusinessPartnerCategory : String(1);
    BusinessPartnerFullName : String(81);
  };
};

