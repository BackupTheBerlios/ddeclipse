do L:=1
while (L>0) {
    if (L=1) {
        do f3
        do L:=3
    } else {
        if (L=2) {
            do f1
            do L:=4
        } else {
            if (L=3) {
                do f2
                do L:=2
            } else {
                if (L=4) {
                    if (p0) {
                        do L:=0
                    } else {
                        do L:=2
                    }
                }
            }
        }
    }
}
