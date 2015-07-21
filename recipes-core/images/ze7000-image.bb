SUMMARY = "Developement Image"
DESCRIPTION = "Image with some basic developement and diagnostic tools"
AUTHOR = "Stefan Eichenberger <stefan.eichenberger@netmodule.com>"
HOMEPAGE = "http://www.netmodule.com"

IMAGE_FEATURES += "ssh-server-openssh package-management"

IMAGE_INSTALL_append = "packagegroup-core-boot \
                bash \
                mtd-utils \
                ethtool \
                usbutils \
                gdbserver \
                iputils \
                devmem2 \
                procps \
                sysstat \
                nfs-utils \
                tcpdump \
                nano \
                iperf \
                bridge-utils \
                i2c-tools \
                bittwist \
                phy-access \
                python-core \
                python-modules \
                python-misc \
                python-pysnmp \
                python-construct \
                lighttpd \
                ruby \
                kernel-devicetree \
                kernel-image \
                kernel-base \
                smart-config \
                ${ROOTFS_PKGMANAGE_BOOTSTRAP} \
                ${CORE_IMAGE_EXTRA_INSTALL} \
                "

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

link_devicetree() {
    ROOTFS=${WORKDIR}/rootfs
    cd $ROOTFS/boot/
    dtb=$(find -name "${MACHINE}.dtb" | head -1)
    bbnote "Found devicetree: ${dtb}"

    if [ -n ${dtb}  ]; then
      ln -s ${dtb} devicetree.dtb
    fi
    cd -
}

IMAGE_PREPROCESS_COMMAND += " link_devicetree "

# Get variable in local or global env
def getVar(var, d, origenv):
    value = origenv.getVar(var, True)
    if value is None:
        value = d.getVar(var, True)

    if value is None:
        bb.warn(var + " is not set, please set it in local.conf or through environment variables")

    return value

# Enhance information in the /etc/version file
python __anonymous() {
    origenv = d.getVar("BB_ORIGENV", False)
    systemVersion = getVar("SYSTEM_VERSION", d, origenv)

    if systemVersion is None:
        systemVersion = "0.0.0"

    buildName = "\"${PN};" + systemVersion + ";${DATETIME}\""
    d.setVar("BUILDNAME" , buildName)

}

# Add default repository for package manager
# For more help on smart, see http://labix.org/smart/howto
add_custom_smart_config() {
    smart --data-dir=${IMAGE_ROOTFS}/var/lib/smart channel --add release \
            type=rpm-md baseurl=http://repository.netmodule.com/ze7000/2015.03/main -y
}

# Function to run after creation of rootfs
ROOTFS_POSTPROCESS_COMMAND += "add_custom_smart_config; "

