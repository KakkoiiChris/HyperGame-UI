package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.state.State
import kakkoiichris.hypergame.ui.layout.Panel
import kakkoiichris.hypergame.view.View

open abstract class UIState(view: View) : State, Panel() {
    init {
        setBounds(view.bounds)
    }
}