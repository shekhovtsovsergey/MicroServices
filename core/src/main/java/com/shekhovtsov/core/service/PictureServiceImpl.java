package com.shekhovtsov.core.service;

import com.shekhovtsov.core.integration.PictureServiceIntegration;
import com.shekhovtsov.core.model.Picture;
import com.shekhovtsov.core.model.Product;
import com.shekhovtsov.core.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureServiceIntegration pictureServiceIntegration;
    private final PictureRepository pictureRepository;

    @Override
    public Picture createPicture(MultipartFile image, Product product) {
        String storageFileName = pictureServiceIntegration.createPicture(image);
        Picture picture = new Picture("image/jpeg", storageFileName, product);
//        product.getPictures().add(picture);
        picture.setProduct(product);
        pictureRepository.save(picture);
        return picture;
    }
}
