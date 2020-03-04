package io.fleanza.mono.oidc;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("io.fleanza.mono.oidc");

        noClasses()
            .that()
                .resideInAnyPackage("io.fleanza.mono.oidc.service..")
            .or()
                .resideInAnyPackage("io.fleanza.mono.oidc.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..io.fleanza.mono.oidc.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
