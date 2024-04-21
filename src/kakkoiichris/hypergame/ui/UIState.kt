package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.state.State
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.View

open abstract class UIState(view: View) : State {
    protected val root = Panel()

    init {
        root.setBounds(view.bounds)
    }

    fun add(module: Module, option: UInt = 0U) =
        root.add(module, option)

    fun remove(module: Module) =
        root.remove(module)

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        root.update(view, manager, time, input)
    }

    override fun render(view: View, renderer: Renderer) {
        root.render(view, renderer)
    }
}