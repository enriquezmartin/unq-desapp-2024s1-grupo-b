package ar.unq.desapp.grupob.backenddesappapi

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class ArchTest {
    lateinit var baseClasses: JavaClasses
    @BeforeEach
    fun setup() {
        baseClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.unq.desapp.grupob.backenddesappapi")
    }

    @Test
    fun layeredArchitectureTest(){
        val rule : ArchRule = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..controller..", "..dataInit..")
            .layer("Service").definedBy("..service..", "..thirdApiService..")
            .layer("Persistence").definedBy("..repository..")
            .layer("Security").definedBy("..security..")

            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Security")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Security").mayNotBeAccessedByAnyLayer()
        rule.check(baseClasses)
    }

    @Test
    fun controllerClassesShouldEndWithController(){
        classes().that().resideInAPackage("..controller")
            .and(object : DescribedPredicate<JavaClass>("are not Kotlin generated classes") {
                override fun test(javaClass: JavaClass): Boolean {
                    return !javaClass.fullName.contains("$")
                }
            })
            .should().haveSimpleNameEndingWith("Controller")
            .check(baseClasses)
    }

    @Test
    fun repositoryClassesShouldEndWithRepository(){
        classes().that().resideInAPackage("..repository")
            .and(object : DescribedPredicate<JavaClass>("are not Kotlin generated classes") {
                override fun test(javaClass: JavaClass): Boolean {
                    return !javaClass.fullName.contains("$")
                }
            }) //Tuve que hacer esta cochinada porque me tomaba una clase creada por kotlin y no podia filtarla
            .should().haveSimpleNameEndingWith("Repository")
            .check(baseClasses)
    }

    @Test
    fun controllerClassesShouldHaveSpringControllerAnnotation(){
        classes().that().resideInAPackage("..controller")
            .and(object : DescribedPredicate<JavaClass>("are not Kotlin generated classes") {
                override fun test(javaClass: JavaClass): Boolean {
                    return !javaClass.fullName.contains("$")
                }
            })
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .check(baseClasses)
    }


}