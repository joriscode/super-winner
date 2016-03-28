import java.net.URL

// POM Model
// https://maven.apache.org/pom.html
// javadoc: https://maven.apache.org/ref/3.3.9/maven-model/apidocs/org/apache/maven/model/Model.html
// 
// the pom is defined using modello
// https://codehaus-plexus.github.io/modello/
// https://github.com/apache/maven/blob/master/maven-model/src/main/mdo/maven.mdo
// 
// propensive xml
// 
// xml -> case class
// https://github.com/propensive/rapture/blob/dev/xml-test/shared/src/main/scala/rapture/xml-test/tests.scala
case class MavenModel(
  groupId: String,
  artifactId: String,
  version: String,
  packaging: String,
  name: String,
  url: Option[URL],
  scm: Option[SourceCodeManagment],
  issueManagement: IssueManagement,
  mailingLists: List[MailingList],

  contributors: List[Contributor],
  developers: List[Contributor],
  licenses: List[License],
  dependencies: List[Dependency],
  repositories: List[Repository],
  organization: Option[Organization]
)

/** Description of a person who has contributed to the project, but who does not
    have commit privileges. Usually, these contributions come in the form of patches submitted. */
case class Contributor(
  name: Option[String],
  email: Option[String],
  url: Option[URL],
  
  // alias
  organization: Option[String],
  organisation: Option[String],

  // alias
  organizationUrl: Option[URL],
  organisationUrl: Option[URL],
  
  roles: List[String],
  /*
  The timezone the contributor is in. Typically, this is a number in the range
  <a href="http://en.wikipedia.org/wiki/UTC%E2%88%9212:00">-12</a> to <a href="http://en.wikipedia.org/wiki/UTC%2B14:00">+14</a>
  or a valid time zone id like "America/Montreal" (UTC-05:00) or "Europe/Paris" (UTC+01:00).
  */
  timezone: Option[String],
  
  // Properties about the contributor, such as an instant messenger handle.
  properties: Map[String, String],

  // Developer
  // The unique ID of the developer in the SCM.
  id: String
)

case class Dependency(
  groupId: String,    // org.apache.maven
  artifactId: String, // maven-artifact
  version: String,    // 3.2.1
  // url (to download if central fails)
  // type (jar, war, plugin)
  // classifier (to distinguish two artifacts)
  properties: Map[String, String],
  scope: Option[String],
  exclusions: Set[Exclusion],
  optional: Boolean
)

case class Exclusion(groupId: String, artifactId: String)

case class Repository(
  // This connection is read-only.
  // ex: scm:git:ssh://github.com/path_to_repository
  connection: URL,
  // This scm connection will not be read only.
  developerConnection: URL,
  // The URL to the project's browsable SCM repository
  url: URL
)

// see Repository
case class SourceCodeManagment(
  connection: URL,
  developerConnection: URL,
  url: URL,
  tag: String
)

case class IssueManagement(
  system: String,
  url: URL
)

case class MailingList(
  name: String,
  subscribe: Option[String],
  unsubscribe: Option[String],
  post: Option[String],
  archive: Option[String],
  otherArchives: Option[String]
)

case class Organization(
  name: String,
  url: URL,
  logo: String
)