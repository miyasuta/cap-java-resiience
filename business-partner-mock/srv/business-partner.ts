import cds from '@sap/cds'
import { BusinessPartners, loadBusinessPartners } from '#cds-models/BusinessPartnerService'

const { DELETE, INSERT } = cds.ql

export class BusinessPartnerService extends cds.ApplicationService { init() {

  this.on (loadBusinessPartners, async (req) => {
    const { number } = req.data as { number: number }

    // Delete all existing BusinessPartners
    await DELETE.from(BusinessPartners)

    // Generate and insert new BusinessPartners
    const businessPartners: any[] = []
    for (let i = 0; i < number; i++) {
      businessPartners.push({
        BusinessPartner: (1000000000 + i).toString(),
        BusinessPartnerCategory: Math.random() < 0.5 ? '1' : '2',
        BusinessPartnerFullName: `Business Partner ${i + 1}`
      })
    }

    await INSERT.into(BusinessPartners).entries(businessPartners)

    return 'Success'
  })

  return super.init()
}}
