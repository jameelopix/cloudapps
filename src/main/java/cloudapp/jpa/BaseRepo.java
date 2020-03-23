package cloudapp.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

//@NoRepositoryBean
public interface BaseRepo<T, ID extends Serializable> { // extends JpaRepository<T, ID> {

	public List<T> search(SearchObject searchObject);

	public Long count(SearchObject searchObject);

	public List<Object[]> search(SearchObject searchObject, List<String> selections);

	public T findOne(ID id);

	public void delete(ID id);
}