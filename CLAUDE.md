## Projects
### business-partner-mock
- This is a CAP Node.js project
- Provides mock API of Business Partners

### cap-java-resilience
- THis is a CAP Java project
- Consumes the mock Business Partner service provided by the Node.js project
- The plan is to add chaching and resilience functionalities provided by SAP Cloud SDK

## MCP Server Usage Rules

### CAP MCP Server (@cap-js/mcp-server)
- You MUST search for CDS definitions, like entities, fields and services (which include HTTP endpoints) with cds-mcp, only if it fails you MAY read *.cds files in the project.
- You MUST search for CAP docs with cds-mcp EVERY TIME you modify CDS models or when using APIs from CAP. Do NOT propose, suggest or make any changes without first checking it.
- Always refer to @cap docs for better information about SAP CAP (Cloud Application Programming) applications.