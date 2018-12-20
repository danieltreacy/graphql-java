package graphql.schema;

import graphql.PublicApi;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionId;
import graphql.execution.ExecutionStepInfo;
import graphql.execution.MergedFields;
import graphql.language.Field;
import graphql.language.FragmentDefinition;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.Map;


/**
 * A DataFetchingEnvironment instance of passed to a {@link DataFetcher} as a execution context and its
 * the place where you can find out information to help you resolve a data value given a graphql field input
 */
@SuppressWarnings("TypeParameterUnusedInFormals")
@PublicApi
public interface DataFetchingEnvironment {

    /**
     * This is the value of the current object to be queried.
     * Or to put it differently: it is the value of the parent field.
     * <p>
     * For the root query, it is equal to {{@link DataFetchingEnvironment#getRoot}
     *
     * @param <T> you decide what type it is
     *
     * @return can be null for the root query, otherwise it is never null
     */
    <T> T getSource();

    /**
     * @return the arguments that have been passed in via the graphql query
     */
    Map<String, Object> getArguments();

    /**
     * Returns true of the named argument is present
     *
     * @param name the name of the argument
     *
     * @return true of the named argument is present
     */
    boolean containsArgument(String name);

    /**
     * Returns the named argument
     *
     * @param name the name of the argument
     * @param <T>  you decide what type it is
     *
     * @return the named argument or null if its not [present
     */
    <T> T getArgument(String name);

    /**
     * Returns a context argument that is set up when the {@link graphql.GraphQL#execute} method
     * is invoked.
     * <p>
     * This is a info object which is provided to all DataFetcher, but never used by graphql-java itself.
     *
     * @param <T> you decide what type it is
     *
     * @return can be null
     */
    <T> T getContext();

    /**
     * This is the source object for the root query.
     *
     * @param <T> you decide what type it is
     *
     * @return can be null
     */
    <T> T getRoot();

    /**
     * @return the definition of the current field
     */
    GraphQLFieldDefinition getFieldDefinition();


    /**
     * Use {@link #getMergedFields()}.
     */
    @Deprecated
    List<Field> getFields();

    /**
     * It can happen that a query has overlapping fields which are
     * are querying the same data. If this is the case they get merged
     * together and fetched only once, but this method returns all of the Fields
     * from the query.
     *
     * Most of the time you probably want to use {@link #getField()}.
     *
     * Example query with more than one Field returned:
     *
     * <pre>
     * {@code
     *
     *      query Foo {
     *          bar
     *          ...BarFragment
     *      }
     *
     *      fragment BarFragment on Query {
     *          bar
     *      }
     * }
     * </pre>
     *
     * @return the list of fields currently queried
     */
    MergedFields getMergedFields();

    /**
     * @return returns the field which is currently queried. See also {@link #getMergedFields()}.
     */
    Field getField();

    /**
     * @return graphql type of the current field
     */
    GraphQLOutputType getFieldType();


    /**
     * @return the field {@link ExecutionStepInfo} for the current data fetch operation
     */
    ExecutionStepInfo getExecutionStepInfo();

    /**
     * @return the type of the parent of the current field
     */
    GraphQLType getParentType();

    /**
     * @return the underlying graphql schema
     */
    GraphQLSchema getGraphQLSchema();

    /**
     * @return the {@link FragmentDefinition} map for the current data fetch operation
     */
    Map<String, FragmentDefinition> getFragmentsByName();

    /**
     * @return the {@link ExecutionId} for the current data fetch operation
     */
    ExecutionId getExecutionId();

    /**
     * @return the {@link DataFetchingFieldSelectionSet} for the current data fetch operation
     */
    DataFetchingFieldSelectionSet getSelectionSet();

    /**
     * @return the current {@link ExecutionContext}. It gives access to the overall schema and other things related to the overall execution of the current request.
     */
    ExecutionContext getExecutionContext();

    /**
     * This allows you to retrieve a named dataloader from the underlying {@link org.dataloader.DataLoaderRegistry}
     *
     * @param dataLoaderName the name of the data loader to fetch
     * @param <K>            the key type
     * @param <V>            the value type
     *
     * @return the named data loader or null
     *
     * @see graphql.execution.ExecutionContext#getDataLoaderRegistry()
     * @see org.dataloader.DataLoaderRegistry#getDataLoader(String)
     */
    <K, V> DataLoader<K, V> getDataLoader(String dataLoaderName);
}
