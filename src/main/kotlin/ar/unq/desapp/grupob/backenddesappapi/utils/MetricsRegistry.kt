package ar.unq.desapp.grupob.backenddesappapi.utils

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsRegistry(meterRegistry: MeterRegistry) {

    val loginAttemptsCounter: Counter = meterRegistry.counter("login_attempts_total")
}
