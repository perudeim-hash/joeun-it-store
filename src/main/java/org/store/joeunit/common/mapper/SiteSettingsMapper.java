package org.store.joeunit.common.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.common.dto.SiteSettingsDto;

@Mapper
public interface SiteSettingsMapper {
    SiteSettingsDto getSettings();
    void updateSettings(SiteSettingsDto dto);
}