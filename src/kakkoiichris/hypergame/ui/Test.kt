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

        root.add(createButton("Dokibird", 10u))
        root.add(createButton("Selen Tatsuki", 15u))
        root.add(createButton("Mint Fantôme", 20u))
        root.add(createButton("Pomu Rainpuff", 25u))
        root.add(createButton("Quinn Benet", 30u))
        root.add(createButton("Kyo Kaneko", 35u))
    }

    private var n = 0

    private fun createButton(text: String, margin: UInt) =
        CheckBox(text).apply {
            this.margin = margin
            id = "button_${n++}"
            font = Font("Times New Roman", Font.PLAIN, 20)
            onChange = { (source, time) -> println("${source.text} ${source.selected}") }
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