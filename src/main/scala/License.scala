import java.net.URL

/*
This element describes all of the licenses for this project.
Each license is described by a <code>license</code> element, which
is then described by additional elements.
Projects should only list the license(s) that applies to the project
and not the licenses that apply to dependencies.
If multiple licenses are listed, it is assumed that the user can select
any of them, not that they must accept all.

Describes the licenses for this project. This is used to generate the license
page of the project's web site, as well as being taken into consideration in other reporting
and validation. The licenses listed for the project are that of the project itself, and not
of dependencies.
 */
sealed trait License {
  // The full legal name of the license
  val name: String

  // The official url for the license text.
  val url: Option[URL]

  /*
  The primary method by which this project may be distributed.
    * repo: may be downloaded from the Maven repository
    * manual: user must manually download and install the dependency
  */
  val distribution: Option[String] = None

  // Addendum information pertaining to this license.
  val comments: Option[String] = None
}

case class RawLicense(
  val name: String,
  val url: Option[URL] = None,
  override val distribution: Option[String] = None,
  override val comments: Option[String] = None
) extends License

sealed trait Spdx extends License {
  val url = Some(new URL(s"https://spdx.org/licenses/$spdxId"))
  def spdxId: String
}

// inspired by: https://github.com/NixOS/nixpkgs/blob/master/lib/licenses.nix#L1

case object Academic extends Spdx {
  val spdxId = "AFL-2.1"
  val name = "Academic Free License"
}

case object Affero extends Spdx {
  val spdxId = "AGPL-3.0"
  val name = "GNU Affero General Public License v3.0"
}

case object Apache1_0 extends Spdx {
  val spdxId = "Apache-2.0"
  val name = "Apache License 2.0"
}

case object Apache1_1 extends Spdx {
  val spdxId = "Apache-2.0"
  val name = "Apache License 2.0"
}

case object Apache2 extends Spdx {
  val spdxId = "Apache-2.0"
  val name = "Apache License 2.0"
}

case object Bsd2Clause extends Spdx {
  val spdxId = "BSD-2-Clause"
  val name = """BSD 2-clause "Simplified" License"""
}

case object Bsd3Clause extends Spdx {
  val spdxId = "BSD-3-Clause"
  val name = """BSD 3-clause "New" or "Revised" License"""
}

case object BsdOriginal extends Spdx {
  val spdxId = "BSD-4-Clause"
  val name = """BSD 4-clause "Original" or "Old" License"""
}

case object CreativeCommonsZeroUniversal extends Spdx {
  val spdxId = "CC0-1.0"
  val name = "Creative Commons Zero v1.0 Universal"
}

case object CreativeCommonsAttributionNonCommercialShareAlike_2_0 extends Spdx {
  val spdxId = "CC-BY-NC-SA-2.0"
  val name = "Creative Commons Attribution Non Commercial Share Alike 2.0"
}

case object CreativeCommonsAttributionNonCommercialShareAlike_2_5 extends Spdx {
  val spdxId = "CC-BY-NC-SA-2.5"
  val name = "Creative Commons Attribution Non Commercial Share Alike 2.5"
}

case object CreativeCommonsAttributionNonCommercialShareAlike_3_0 extends Spdx {
  val spdxId = "CC-BY-NC-SA-3.0"
  val name = "Creative Commons Attribution Non Commercial Share Alike 3.0"
}

case object CreativeCommonsAttributionNonCommercialShareAlike_4_0 extends Spdx {
  val spdxId = "CC-BY-NC-SA-4.0"
  val name = "Creative Commons Attribution Non Commercial Share Alike 4.0"
}

case object CreativeCommonsAttributionShareAlike_2_5 extends Spdx {
  val spdxId = "CC-BY-SA-2.5"
  val name = "Creative Commons Attribution Share Alike 2.5"
}

case object CreativeCommonsAttribution_3_0 extends Spdx {
  val spdxId = "CC-BY-3.0"
  val name = "Creative Commons Attribution 3.0"
}

case object CreativeCommonsAttributionShareAlike_3_0 extends Spdx {
  val spdxId = "CC-BY-SA-3.0"
  val name = "Creative Commons Attribution Share Alike 3.0"
}

case object CreativeCommonsAttribution_4_0 extends Spdx {
  val spdxId = "CC-BY-4.0"
  val name = "Creative Commons Attribution 4.0"
}

case object CreativeCommonsAttributionShareAlike_4_0 extends Spdx {
  val spdxId = "CC-BY-SA-4.0"
  val name = "Creative Commons Attribution Share Alike 4.0"
}

case object Eclipse extends Spdx {
  val spdxId = "EPL-1.0"
  val name = "Eclipse Public License 1.0"
}

case object GPL1 extends Spdx {
  val spdxId = "GPL-1.0"
  val name = "GNU General Public License v1.0 only"
}

case object GPL1_Plus extends Spdx {
  val spdxId = "GPL-1.0+"
  val name = "GNU General Public License v1.0 or later"
}

case object GPL2 extends Spdx {
  val spdxId = "GPL-2.0"
  val name = "GNU General Public License v2.0 only"
}

case object GPL2Plus extends Spdx {
  val spdxId = "GPL-2.0+"
  val name = "GNU General Public License v2.0 or later"
}

case object GPl3 extends Spdx {
  val spdxId = "GPL-3.0"
  val name = "GNU General Public License v3.0 only"
}

case object GPL3Plus extends Spdx {
  val spdxId = "GPL-3.0+"
  val name = "GNU General Public License v3.0 or later"
}

case object LGPL2 extends Spdx {
  val spdxId = "LGPL-2.0"
  val name = "GNU Library General Public License v2 only"
}

// deprecated
case object LGPL2_Plus extends Spdx {
  val spdxId = "LGPL-2.0+"
  val name = "GNU Library General Public License v2 or later"
}

case object LGPL2_1 extends Spdx {
  val spdxId = "LGPL-2.1"
  val name = "GNU Library General Public License v2.1 only"
}

// deprecated
case object LGPL2_1_Plus extends Spdx {
  val spdxId = "LGPL-2.1+"
  val name = "GNU Library General Public License v2.1 or later"
}

case object LGPL3 extends Spdx {
  val spdxId = "LGPL-3.0"
  val name = "GNU Lesser General Public License v3.0 only"
}

// deprecated
@deprecated("use LGPL3", "2.0rc2")
case object LGPL3_Plus extends Spdx {
  val spdxId = "LGPL-3.0+"
  val name = "GNU Lesser General Public License v3.0 or later"
}

// Spdx.org does not (yet) differentiate between the X11 and Expat versions
// for details see http://en.wikipedia.org/wiki/MIT_License#Various_versions
case object MIT extends Spdx {
  val spdxId = "MIT"
  val name = "MIT License"
}

case object MPL_1_0 extends Spdx {
  val spdxId = "MPL-1.0"
  val name = "Mozilla Public License 1.0"
}

case object MPL_1_1 extends Spdx {
  val spdxId = "MPL-1.1"
  val name = "Mozilla Public License 1.1"
}

case object MPL2 extends Spdx {
  val spdxId = "MPL-2.0"
  val name = "Mozilla Public License 2.0"
}

case object PublicDomain extends License {
  val name = "Public Domain"
  val url = None
}

case object Scala extends License {
  val name = "Scala License"
  val url = Some(new URL("http://www.scala-lang.org/license.html"))
}

case object W3C extends Spdx {
  val spdxId = "W3C"
  val name = "W3C Software Notice and License"
}

case object WTFPL extends Spdx {
  val spdxId = "WTFPL"
  val name = "Do What The F*ck You Want To Public License"
}