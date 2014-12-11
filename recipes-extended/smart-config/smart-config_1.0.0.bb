SUMMARY = "Smart package manager autoconfiguration helper script"
DESCRIPTION = "Autconfigure smart repositories from a configuration file"
AUTHOR = "Guy Morand <guy.morand@netmodule.ch>"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
HOMEPAGE = "http://www.netmodule.com"
PACKAGE_ARCH="all"
PR="r0"

S="${WORKDIR}"

SRC_URI = " \
  file://smart-config \
  file://smart_channels \
  "

CONFFILES_${PN} += "${sysconfdir}/default/smart_channels"

do_install () {
  install -D -m 755 ${S}/smart-config ${D}/${bindir}/smart-config
  install -D -m 644 ${S}/smart_channels ${D}/${sysconfdir}/default/smart_channels
}

pkg_postinst_${PN} () {
  # Force package installation at first boot on target
  [ ! -z "$D" ] && exit 1

  if [ "$(ps | grep "python $(which smart)" | grep -v grep)" = "" ]
  then
    smart-config -a
  else
    echo "Cannot update smart channels list, smart is already running!"
    echo "You can update the smart channel list by running this command "
    echo "manually:"
    echo "  smart-config -a"
  fi
}

