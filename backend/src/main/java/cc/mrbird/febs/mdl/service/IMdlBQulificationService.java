package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBQulification;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author viki
 * @since 2022-01-12
 */
public interface IMdlBQulificationService extends IService<MdlBQulification> {

        IPage<MdlBQulification> findMdlBQulifications(QueryRequest request, MdlBQulification mdlBQulification);

        IPage<MdlBQulification> findMdlBQulificationList(QueryRequest request, MdlBQulification mdlBQulification);

        void createMdlBQulification(MdlBQulification mdlBQulification);

        void updateMdlBQulification(MdlBQulification mdlBQulification);

        void deleteMdlBQulifications(String[]Ids);


        }
