//
// Decompiled by Procyon v0.5.30
//

package com.camtrack;

import java.util.Arrays;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.camtrack.config.Encryption;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;*/
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
/**
 * import springfox.documentation.builders.ParameterBuilder; import
 * springfox.documentation.builders.PathSelectors; import
 * springfox.documentation.builders.RequestHandlerSelectors; import
 * springfox.documentation.schema.ModelRef; import
 * springfox.documentation.schema.ModelReference; import
 * springfox.documentation.service.ApiInfo; import
 * springfox.documentation.service.Contact; import
 * springfox.documentation.service.Parameter; import
 * springfox.documentation.spi.DocumentationType; import
 * springfox.documentation.spring.web.plugins.Docket; import
 * springfox.documentation.swagger.web.DocExpansion; import
 * springfox.documentation.swagger.web.ModelRendering; import
 * springfox.documentation.swagger.web.OperationsSorter; import
 * springfox.documentation.swagger.web.TagsSorter; import
 * springfox.documentation.swagger.web.UiConfiguration; import
 * springfox.documentation.swagger.web.UiConfigurationBuilder; import
 * springfox.documentation.swagger2.annotations.EnableSwagger2;
 */

@EnableDiscoveryClient
@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RefreshScope
//@EnableSwagger2
//@OpenAPIDefinition(servers = {@Server(url = "/ymane", description = "Default Server URL")})
@EnableEncryptableProperties
@EnableCaching
/**
 * @OpenAPIDefinition(info = @Info(title = "Video API", description =
 *                         "Documentation Ymanege API v1.0", version = "2.0",
 *                         contact = @Contact( name = "Camtrack Team", email =
 *                         "suppport@camtrack.net", url = "https://camtrack.net"
 *                         ), license = @License( name = "Hilaire Tinen", url =
 *                         "https://camtrack.net" ) ), servers = {@Server(url =
 *                         "/ymane", description = "Default Server URL")},
 *                         security = @SecurityRequirement(name = "JWT"),
 *                         components = @Components( securitySchemes
 *                         = @SecurityScheme( securitySchemeName = "JWT",
 *                         description = "JWT authentication with bearer token",
 *                         type = SecuritySchemeType.HTTP, scheme = "bearer",
 *                         bearerFormat = "Bearer [token]")))
 */
public class Video {
	public static void main(final String[] args) {
		String decrypt = "";
		try {
			decrypt = Encryption.decrypt("jqeeuYXfoLnQM0sdazxiEQ==");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpringApplication.run((Class) Video.class, args);
	}

	final String bearerToken = "This value is Mandatory ; Format Token:   bearer xxxxx";

	@Value("${enable.swagger.plugin}")
	public boolean enableSwaggerPlugin;

	/**
	 * @Bean public OpenAPI customOpenAPI() { return new OpenAPI() .components(new
	 *       Components() .addSecuritySchemes(securitySchemeName, new
	 *       SecurityScheme() .type(SecurityScheme.Type.HTTP) .scheme("bearer")
	 *       .bearerFormat("JWT").description(securitySchemeName)))
	 *       .info(apiInfo()); }
	 */

	private Info apiInfo() {
		return new Info().title("Video API").description("Documentation Ymanege API v1.0").version("2.0")
				.contact(apiContact()).license(apiLicence());
	}

	private License apiLicence() {
		return new License().name("MIT Licence").url("https://opensource.org/licenses/mit-license.php");
	}

	private Contact apiContact() {
		return new Contact().name("Team Developpement").email("innovation@camtrack.net").url("https://camtrack.net");
	}

	/**
	 * private ApiInfo apiInfo() { return new ApiInfo("Video Rest API", "Services
	 * For Video Dashboard Management.", "API TOS", "Terms of service", new
	 * Contact("Hilaire Tinen", "https://camtrack.net", "support@camtrack.net"),
	 * "License of API", "API license URL", (Collection) Collections.emptyList()); }
	 */

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "bearerAuth";

		return new OpenAPI().servers(Arrays.asList(new Server().description("Default Video Server URL").url("/ymane")))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().type(SecurityScheme.Type.HTTP).in(SecurityScheme.In.HEADER)
								.name(HttpHeaders.AUTHORIZATION).bearerFormat("Bearer [token]").scheme("bearer")))
				.info(apiInfo());
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC+1"));
	}

	@Bean(name = { "multipartResolver" })
	public CommonsMultipartResolver multipartResolver() {
		final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000000L);
		return multipartResolver;
	}
	/**
	 * @Bean public Docket api() { final ParameterBuilder aParameterBuilder = new
	 *       ParameterBuilder();
	 *       aParameterBuilder.name("Authorization").modelRef((ModelReference) new
	 *       ModelRef("string")) .parameterType("header").required(true).build();
	 *       final List<Parameter> aParameters = new ArrayList<Parameter>();
	 *       aParameters.add(aParameterBuilder.build()); return new
	 *       Docket(DocumentationType.SWAGGER_2).apiInfo(this.apiInfo()).select()
	 *       .apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex("(?!/error).+"))
	 *       .paths(PathSelectors.regex("(?!/oauth).+"))
	 *       .paths(PathSelectors.any()).build().enable(enableSwaggerPlugin).globalOperationParameters((List)
	 *       aParameters).enable(enableSwaggerPlugin); }
	 */
	/**
	 * @Bean UiConfiguration uiConfig() { return
	 *       UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(1)
	 *       .defaultModelExpandDepth(1).defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(false)
	 *       .docExpansion(DocExpansion.NONE).filter((Object)
	 *       false).maxDisplayedTags((Integer) null)
	 *       .operationsSorter(OperationsSorter.ALPHA).showExtensions(false).tagsSorter(TagsSorter.ALPHA)
	 *       .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl((String)
	 *       null) .build(); }
	 */
}
