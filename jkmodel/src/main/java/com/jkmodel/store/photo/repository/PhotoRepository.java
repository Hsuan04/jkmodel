package com.jkmodel.store.photo.repository;

import com.jkmodel.store.photo.dto.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Integer>{
}
