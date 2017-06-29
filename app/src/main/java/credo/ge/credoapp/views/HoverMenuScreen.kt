package credo.ge.credoapp.views


import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.github.barteksc.pdfviewer.PDFView
import credo.ge.credoapp.R

import credo.ge.credoapp.StaticData
import io.mattcarroll.hover.Content
import kotlinx.android.synthetic.main.float_body.*

/**
 * Created by kaxge on 6/29/2017.
 */

class HoverMenuScreen(context: Context, private val mPageTitle: String) : Content {
    private val mContext: Context
    private val mWholeScreen: View

    init {
        mContext = context.applicationContext
        mWholeScreen = createScreenView()
    }

    private fun createScreenView(): View {

        var body =  LayoutInflater.from(mContext).inflate(R.layout.float_body, null)
        val pdfView = body.findViewById(R.id.pdfView) as PDFView
        pdfView.fromBytes(StaticData.pdf)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAntialiasing(true)
                .load()

        return body

    }

    // Make sure that this method returns the SAME View.  It should NOT create a new View each time
    // that it is invoked.
    override fun getView(): View {
        var body =  LayoutInflater.from(mContext).inflate(R.layout.float_body, null)
        val pdfView = body.findViewById(R.id.pdfView) as PDFView
        pdfView.fromBytes(StaticData.pdf)
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAntialiasing(true)
                .load()

        return body
    }

    override fun isFullscreen(): Boolean {
        return true
    }

    override fun onShown() {
        // No-op.
    }

    override fun onHidden() {
        // No-op.
    }
}
