package com.play.modulestructure.utils.logging

import io.github.oshai.kotlinlogging.KotlinLogging


class Logger {
    companion object {
        val logger = KotlinLogging.logger {}

        fun info(messageProvider: () -> String) { logger.info(messageProvider) }

        fun debug(messageProvider: () -> String) { logger.debug(messageProvider) }

        fun warn(messageProvider: () -> String) { logger.warn(messageProvider) }

        fun error(messageProvider: () -> String) { logger.error(messageProvider) }

    }
}