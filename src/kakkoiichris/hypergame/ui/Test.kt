package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.input.Key
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.Display
import kakkoiichris.hypergame.view.View
import java.awt.Color
import java.awt.Font

class Test(view: View) : UIState(view) {
    init {
        root.layout = Layout.HardGrid(3u, 3u)

        val group = RadioButton.Group()

        root.add(group.createButton("Dokibird", 10u, Color(255, 200, 0)))
        root.add(group.createButton("Selen Tatsuki", 15u, Color(150, 100, 255)))
        root.add(group.createButton("Mint Fantôme", 20u, Color(100, 200, 170)))
        root.add(group.createButton("Pomu Rainpuff", 25u, Color(50, 150, 150)))
        root.add(group.createButton("Quinn Benet", 30u, Color(150, 50, 150)))
        root.add(group.createButton("Kyo Kaneko", 35u, Color(110, 120, 200)))
    }

    private var n = 0

    private fun RadioButton.Group.createButton(text: String, margin: UInt, accent: Color) =
        create(text).apply {
            this.margin = margin
            this.accent = accent
            id = "button_${n++}"
            font = Font("Times New Roman", Font.PLAIN, 20)
            eventListener = { (source, time) -> println("${source.selected} @ ${time}<$text>") }
        }

    override fun swapTo(view: View) {
    }

    override fun swapFrom(view: View) {
    }

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        super.update(view, manager, time, input)

        if (input.inWindow && input.keyHeld(Key.SPACE)) {
            root.width = input.x.toDouble()
            root.height = input.y.toDouble()
        }
    }

    override fun halt(view: View) {
    }
}

fun main() {
    val display = Display(800, 800, title = "HYPERGAME UI TEST")

    display.manager.push(Test(display))

    display.open()
}