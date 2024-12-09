package ch.admin.wbf.isceco.db;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeCasePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private static final String REGEX = "([a-z]+)([A-Z]+)";
    private static final String REPLACEMENT_REGEX = "$1_$2";

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return super.toPhysicalCatalogName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return super.toPhysicalColumnName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return super.toPhysicalSchemaName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return super.toPhysicalSequenceName(toSnakeCase(name), context);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return super.toPhysicalTableName(toSnakeCase(name), context);
    }


    private Identifier toSnakeCase(Identifier id) {
        if (id != null) {
            String name = id.getText();

            String snakeName = name.replaceAll(REGEX, REPLACEMENT_REGEX).toLowerCase(); // NOSONAR

            if (snakeName.equals(name)) {
                return id;
            } else {
                return new Identifier(snakeName, id.isQuoted());
            }
        }
        return null;

    }

}
