import cds from '@sap/cds'
import { BusinessPartners } from '#cds-models/BusinessPartnerService'

export class BusinessPartnerService extends cds.ApplicationService { async init() {
  const bupa = await cds.connect.to('BusinessPartnerA2X')

  this.on ('READ', BusinessPartners, async (req) => {
    return bupa.run(req.query)
  })

  return super.init()
}}
