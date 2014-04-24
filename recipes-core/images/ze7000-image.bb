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
                bridge-utils \
                netperf \
                i2c-tools \
                fuse \
                bittwist \
                phy-access \
                python-core \
                python-modules \
                python-misc \
                python-pysnmp \
                python-construct \
                ${ROOTFS_PKGMANAGE_BOOTSTRAP} \
                ${CORE_IMAGE_EXTRA_INSTALL} \
                "
#IMAGE_INSTALL_append = "tcpdump nano bridge-utils netperf i2c-tools fuse"
                
                
IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

