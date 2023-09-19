package md5.end.service.amapper;

public interface IGenericMapper<T,K,V> {
    // T entity, K request, V response
    T getEntityFromRequest(K k);
    V getResponseFromEntity(T t);
}