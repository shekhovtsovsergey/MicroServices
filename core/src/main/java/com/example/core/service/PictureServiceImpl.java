package com.example.core.service;

import com.example.core.dao.PictureDao;
import com.example.core.dto.PictureDto;
import com.example.core.integration.PictureServiceIntagration;
import com.example.core.model.Picture;
import com.example.core.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureServiceIntagration pictureServiceIntagration;
    private final PictureDao pictureDao;

    @Override
    public Optional<PictureDto> getPictureDataById(long id) {
        Optional<Picture> picture = pictureDao.findPictureByProductId(id);
        PictureDto pictureDto = pictureServiceIntagration.getPicture(picture.get().getStorageFileName());
        pictureDto.setContentType(picture.get().getContentType());
        return Optional.ofNullable(pictureDto);
    }

    @Override
    public Picture createPicture(MultipartFile image, Product product) {
        String storageFileName = pictureServiceIntagration.createPicture(image);
        Picture picture = new Picture("image/jpeg", storageFileName, product);
        product.getPictures().add(picture);
        picture.setProduct(product);
        pictureDao.save(picture);
        return picture;
    }
}
