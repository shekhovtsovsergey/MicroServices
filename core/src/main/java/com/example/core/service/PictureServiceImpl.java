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
@Slf4j
public class PictureServiceImpl implements PictureService {


    private final PictureServiceIntagration pictureServiceIntagration;
    private final PictureDao pictureDao;


    @Override
    public Optional<PictureDto> getPictureDataById(long id) {
        System.out.println("Picture service " + id);
        Optional<Picture> picture = pictureDao.findPictureByProductId(id);
        System.out.println("нашли в БД " + picture);
        PictureDto pictureDto = pictureServiceIntagration.getPicture(picture.get().getStorageFileName());
        System.out.println("получили из интегации " + picture);
        pictureDto.setContentType(picture.get().getContentType());
        return Optional.ofNullable(pictureDto);
    }



/*
    @Override
    public String createPicture(byte[] pictureData) {
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get("", filename))) {
            os.write(pictureData);
        } catch (IOException ex) {
            log.error("Can't write to file", ex);
            throw new RuntimeException(ex);
        }
        return filename;
    }*/


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
