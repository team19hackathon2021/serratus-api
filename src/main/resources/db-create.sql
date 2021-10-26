-- Define the SQL to create all the tables.

CREATE TABLE SiteUser(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, archived boolean
	, deleted boolean
	, sessionId text
	, userKey bigint
	, userId text
	, userName text
	, userEmail text
	, userFirstName text
	, userLastName text
	, userFullName text
	);
CREATE TABLE SequenceRun(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, archived boolean
	, deleted boolean
	, sessionId text
	, userKey bigint
	, runId text
	, runReleaseDate timestamp with time zone
	, runLoadDate timestamp with time zone
	, projectId text
	, bioSampleId text
	, bioSampleIsolate text
	, bioSampleHost text
	, bioSampleLabHost text
	, bioSampleIsolationSource text
	, bioSampleGeographicLocation text
	, bioSampleLatitudeLongitude text
	, bioSampleReferenceForBioMaterial text
	, bioSampleMaterialProcessing text
	, bioSampleMaterialIdentifiers text
	, bioSampleDisease text
	, bioSampleHostTissueSampled text
	, bioSampleCollectedBy text
	, taxonomyId text
	, taxonomyName text
	, taxonomyUniqueName text
	, taxonomyNameClass text
	);
CREATE TABLE BioSample(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, archived boolean
	, deleted boolean
	, sessionId text
	, userKey bigint
	, bioSampleId text
	, bioSampleIsolate text
	, bioSampleHost text
	, bioSampleLabHost text
	, bioSampleIsolationSource text
	, bioSampleGeographicLocation text
	, bioSampleLatitudeLongitude text
	, bioSampleReferenceForBioMaterial text
	, bioSampleMaterialProcessing text
	, bioSampleMaterialIdentifiers text
	, bioSampleDisease text
	, bioSampleHostTissueSampled text
	, bioSampleCollectedBy text
	, taxonomyId text
	, taxonomyName text
	, taxonomyUniqueName text
	, taxonomyNameClass text
	);
CREATE TABLE Taxonomy(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, archived boolean
	, deleted boolean
	, sessionId text
	, userKey bigint
	, taxonomyId text
	, taxonomyName text
	, taxonomyUniqueName text
	, taxonomyNameClass text
	);