package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.view.View
import java.awt.Font

class Label(var text: String) : Module() {
    var font = Font("Monospaced", Font.PLAIN, 15)

    override fun render(view: View, renderer: Renderer) {
        renderer.color = background

        renderer.fillRoundRect(this, cornerRadius, cornerRadius)

        renderer.color = foreground

        renderer.font = font

        renderer.drawString(text, this)

        renderer.stroke = stroke
        renderer.color = accent

        val sw = renderer.fontMetrics.stringWidth(text)
        val sh = renderer.fontMetrics.height

        val cx = (x + (width - sw) / 2).toInt()
        val cy = (y + (height - sh) / 2 + renderer.fontMetrics.ascent * 1.5).toInt()

        renderer.drawLine(cx, cy, cx + sw, cy)
    }
}