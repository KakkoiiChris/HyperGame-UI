package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.util.Time

interface UIEvent<X : Module> {
    val source: X
    val time: Time
}