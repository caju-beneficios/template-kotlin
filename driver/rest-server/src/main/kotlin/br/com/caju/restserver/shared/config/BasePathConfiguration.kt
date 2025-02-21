package br.com.caju.restserver.shared.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class BasePathConfiguration(val basePathData: BasePathConfigurationData) : WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix(basePathData.prefix) { handlerType ->
            handlerType.isAnnotationPresent(RestController::class.java) &&
                basePathData.ignoredHandlers.none {
                    handlerType.name.contains(it, ignoreCase = true)
                }
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/**")
            .addResourceLocations(
                "classpath:/META-INF/resources/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/",
            )
    }
}
