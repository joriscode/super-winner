// object LicenseCleanup {

//   val equivalent = Map(
//     "Apache-1.1"-> List(Apache1_1),
//     "MITAPACHE-2.0"
//     "The MIT LicenseGNU General Public License, version 2 (GPL-2.0)"
//     "GNU GENERAL PUBLIC LICENSE, Version 3.0"
//     "GNU Lesser GPL"
//     "Apache-2.0MIT"
//     "Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License\n"
//     "BSD style"
//     "BSD 3 Clause"
//     "NetBSD"
//     "BSDBSD"
//     "LGPL (GNU Lesser General Public License)"
//     "GNU Gpl v3"
//     "WTFPL, Version 2"
//     "AGPL-3.0"
//     "Atlassian 3.0 End User License Agreement"
//     "GPL"
//     "MIT Java Wordnet Interface License"
//     "GNU LESSER GENERAL PUBLIC LICENSE, Version 3"
//     "Eclipse Public License - v 1.0"
//     "MIT-style license"
//     "Apache-2.0Apache-2.0Apache License"-> List(Apache2),
//     "Eclipse Public License v1.0"
//     "GNU LesserGPLv3"
//     "GPL 3.0 "
//     "LGPLv2.1"
//     "BSD NewBSD 3-Clause"
//     "WQuery License (BSD-style)"
//     "Apache License Version 2.0The New BSD License"
//     "Apache-2.0The Apache Software License, Version 2.0"
//     "AGPL v3"
//     "Beerware"
//     "Apache-2.0Apache 2.0"-> List(Apache2),
//     "Simplified BSD License"
//     "wtfpl"
//     "BSD 3 clause"
//     "BSD 3-clause License"
//     "AThe MIT License (MIT)"
//     "Open IE Software License Agreement"
//     "The (New) BSD License"
//     "Apache 2 license"-> List(Apache2),
//     "Apache License v2.0"-> List(Apache2),
//     "BSD SimplifiedBSD Simplified"
//     "ISC License"
//     "Modified BSD"
//     "ISC"
//     "Apache License, Version 2"-> List(Apache2),
//     "Apache-2.0Apache License"-> List(Apache2),
//     "lgpl"
//     "BSDBSD-style"
//     "Academic License (for original lex files)Apache 2.0 (for supplemental code)"
//     "GNU GPL v3"
//     "Creative Commons Attribution-ShareAlike 4.0 International"
//     "Malt Parser License"
//     "W3C License"
//     "GPL v2"
//     "LGPL v2+"
//     "LGPL-2.1"
//     "AGPL3"
//     "BSD-Style"
//     "MPL-2.0MPL-2.0"
//     "3-clause BSD"
//     "Public domainBSD-like"
//     "Modified Apache"
//     "GPL-2.0"
//     "Ollie Software License Agreement"
//     "AGPL"                                                   ->
//     "LGPLv3"                                                 -> List(LGPL3)
//     "Apache 2.0Academic License (for original lex files)Apache 2.0 (for supplemental code)"-> List(Apache2),
//     "BSD 3-Clause License"                                   -> List(Bsd3Clause),
//     "GNU General Public License v2"                          -> List(GPL2),
//     "Apache License, Verision 2.0"                           -> List(Apache2),
//     "GNU General Public License v3.0"                        -> List(GPL3)
//     "Academic License"                                       -> List(Academic)
//     "GNU General Public License (GPL)"                       -> List(GPL3), // ambigous
//     "AGPL-V3"                                                -> List(Affero)
//     "Apache Public License 2.0"                              -> List(Apache2),
//     "New BSD License"                                        -> List(Bsd3Clause),
//     "GNU Lesser General Public License, Version 2.1Eclipse Public License, Version 1.0" -> List(Eclipse, LGPL2),
//     "New BSD"                                                -> List(Bsd3Clause),
//     "Apache-2.0Apache License, Version 2.0"                  -> List(Apache2),
//     "Mozilla Public License Version 2.0"                     -> List(MPL2),
//     "MPL-2.0W3C"                                             -> List(MPL2, W3C),
//     "The BSD 2-Clause License"                               -> List(Bsd2Clause),
//     "LGPLv3+"                                                -> List(LGPL3_Plus),
//     "DO WHAT YOU WANT TO PUBLIC LICENSE, Version 1"          -> List(WTFPL),
//     "GNU Lesser General Public Licence"                      -> List(GPL3), // ambigous
//     "APSL-2.0"                                               -> List(Apache2),
//     "LGPL3"                                                  -> List(LGPL3)
//     "CC BY 4.0"                                              -> List(CreativeCommonsAttribution_4_0)
//     "Scala license"                                          -> List(Scala),
//     "Apache 2 License"                                       -> List(Apache2),
//     "MIT licencse"                                           -> List(MIT),
//     "GNU Library or Lesser General Public License (LGPL)"    -> List(LGPL3), // ambigous
//     "GPL-3.0"                                                -> List(GPL3),
//     "MIT Licence"                                            -> List(MIT),
//     "The Apache Software Licence, Version 2.0"               -> List(Apache2),
//     "LGPL 3.0 license"                                       -> List(LGPL3),
//     "The Apache License, ASL Version 2.0"                    -> List(Apache2),
//     "GPL 2.0 "                                               -> List(GPL2),
//     "GNU Lesser General Public License v3.0"                 -> List(LGPL3),
//     "Public domain"                                          -> List(PublicDomain),
//     "Apache-2.0Apache 2"                                     -> List(Apache2),
//     "GPL3"                                                   -> List(GPL3),
//     "MITBSD New"                                             -> List(MIT, Bsd3Clause),
//     "The BSD 3-Clause License"                               -> List(Bsd3Clause),
//     "mit"                                                    -> List(MIT),
//     "Apache v2"                                              -> List(Apache2),
//     "Scala License"                                          -> List(Scala),
//     "BSD Simplified"                                         -> List(Bsd2Clause),
//     "BSD 2-Clause License"                                   -> List(Bsd2Clause),
//     "Apache license"                                         -> List(Apache2),
//     "MITBSD-style"                                           -> List(MIT, BsdOriginal), // ambigous
//     "BSD-2-Clause"                                           -> List(Bsd2Clause),
//     "Apache License, ASL Version 2.0"                        -> List(Apache2),
//     "BSD Software License, 2-clause version"                 -> List(Bsd2Clause),
//     "BSD 3-clause"                                           -> List(Bsd3Clause),
//     "Apache License, Verison 2.0"                            -> List(Apache2),
//     "LGPL v3+"                                               -> List(LGPL3_Plus),
//     "GPL version 3 or any later version"                     -> List(GPL3Plus),
//     "APACHE-2.0"                                             -> List(Apache2),
//     "Apache 2.0 "                                            -> List(Apache2),
//     "The MIT License (MIT)"                                  -> List(MIT),
//     "BSD 3-Clause"                                           -> List(Bsd3Clause),
//     "LGPL-3.0"                                               -> List(LGPL3),
//     "GNU Lesser General Public License, Version 2.1"         -> List(LGPL2_1),
//     "The Apache License, Version 2.0"                        -> List(Apache2),
//     "Apache License"                                         -> List(Apache2),
//     "Two-clause BSD-style license"                           -> List(Bsd2Clause),
//     "Three-clause BSD-style"                                 -> List(Bsd3Clause),
//     "BSD-3-Clause"                                           -> List(Bsd3Clause),
//     "GPLv3"                                                  -> List(LGPL3),
//     "W3C"                                                    -> List(W3C),
//     "ASL"                                                    -> List(Apache2),
//     "LGPL v3"                                                -> List(LGPL3),
//     "Apache License Version 2.0"                             -> List(Apache2),
//     "BSD-like"                                               -> List(BsdOriginal) // ambigous
//     "GPL v3+"                                                -> List(GPL3Plus),
//     "The MIT License"                                        -> List(MIT),
//     "Apache2"                                                -> List(Apache2),
//     "Apache License 2.0"                                     -> List(Apache2),
//     "Apache-style"                                           -> List(Apache2),
//     "Apache License, Version 2.0Apache License, Version 2.0" -> List(Apache2),
//     "BSD New"                                                -> List(Bsd2Clause),
//     "the Apache License, ASL Version 2.0"                    -> List(Apache2),
//     "MPL-2.0"                                                -> List(MPL2),
//     "Apache 2.0 License"                                     -> List(Apache2),
//     "Apache V2"                                              -> List(Apache2),
//     "LGPL v2.1+"                                             -> List(LGPL2_1_Plus),
//     "BSD"                                                    -> List(BsdOriginal),  // ambigous
//     "LGPL"                                                   -> List(LGPL3),        // ambigous
//     "MIT license"                                            -> List(MIT), 
//     "Apache"                                                 -> List(Apache2),
//     "Apache 2.0"                                             -> List(Apache2),
//     "GPL v2+"                                                -> List(GPL2Plus)
//     "Affero GPLv3"                                           -> List(Affero3)
//     ""                                                       -> List(),             // no license ?
//     "The Apache Software License, Version 2.0"               -> List(Apache2),
//     "MIT-style"                                              -> List(MIT),          // ambigous
//     "Apache-2.0"                                             -> List(Apache2),
//     "MIT License"                                            -> List(MIT)
//     "BSD-style"                                              -> List(BsdOriginal)   // ambigous
//     "MIT"                                                    -> List(MIT),
//     "Apache License, Version 2.0"                            -> List(Apache2),
//     "Apache 2"                                               -> List(Apache2)
//   )
// }