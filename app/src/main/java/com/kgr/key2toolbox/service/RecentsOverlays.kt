package com.kgr.key2toolbox.service

/** The Recents overlay windows (vertical list / quilt, and grid); at most one is up at a time. */
object RecentsOverlays {
    fun isShowing(): Boolean =
        SlimRecentsOverlayController.isShowing() || GridRecentsOverlayController.isShowing()

    /**
     * Closes whichever is showing. [animate] = false removes it at once (screen off, teardown).
     * [expandTaskId] only applies to the Masonry quilt (see [SlimRecentsOverlayController.hide]); the grid
     * only expands the tile you tap, so Back / Home / Toolbelt close it with a plain fade.
     */
    fun hide(animate: Boolean = true, expandTaskId: Int? = SlimRecentsOverlayController.EXPAND_HERO) {
        if (SlimRecentsOverlayController.isShowing()) SlimRecentsOverlayController.hide(animate, expandTaskId)
        if (GridRecentsOverlayController.isShowing()) GridRecentsOverlayController.hide(animate)
    }
}
