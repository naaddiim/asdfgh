package id.bangkit.facetrack.facetrack.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.bangkit.facetrack.facetrack.dto.ScanDTO;
import id.bangkit.facetrack.facetrack.entity.Scan;
import id.bangkit.facetrack.facetrack.mappers.MapTo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScanMapperImpl implements MapTo<Scan, ScanDTO> {
    private final ModelMapper modelMapper;
    @Override
    public ScanDTO mapTo(Scan scan) {
        return modelMapper.map(scan, ScanDTO.class);
    }
    
}
