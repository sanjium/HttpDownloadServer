package com.download.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.download.entity.domain.Transfer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface TransferMapper extends BaseMapper<Transfer> {


    @Select("SELECT id from download_transfer where status=#{status}}")
    List<Transfer> list(@RequestParam("status") String status);
}
