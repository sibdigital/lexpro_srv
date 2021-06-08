package ru.sibdigital.lexpro.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.dto.RegRkkFileDto;
import ru.sibdigital.lexpro.model.*;

public interface RkkFileService {

    RegRkkFile constructRkkFile(MultipartFile part, RegRkkFileDto regRkkFileDto);
    RegRkkFile editRkkFile(RegRkkFileDto regRkkFileDto);
    RegRkkFile getRegRkkFile(RegRkkFileDto regRkkFileDto);
    void deleteRkkFile(RegRkkFileDto rkkFileDto);
    void savePreviousFileCopy(RegRkkFile rkkFile);
}
