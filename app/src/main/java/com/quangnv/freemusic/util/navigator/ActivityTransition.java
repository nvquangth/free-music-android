package com.quangnv.freemusic.util.navigator;

import android.support.annotation.IntDef;

/**
 * Created by quangnv on 11/10/2018
 */

@IntDef({ ActivityTransition.NONE, ActivityTransition.START, ActivityTransition.FINISH })
@interface ActivityTransition {
    int NONE = 0x00;
    int START = 0x01;
    int FINISH = 0x02;
}
