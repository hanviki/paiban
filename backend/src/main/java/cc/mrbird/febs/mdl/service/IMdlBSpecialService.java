package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.mdl.entity.MdlBSpecial;
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
 * @since 2022-03-16
 */
public interface IMdlBSpecialService extends IService<MdlBSpecial> {

        IPage<MdlBSpecial> findMdlBSpecials(QueryRequest request, MdlBSpecial mdlBSpecial);

        IPage<MdlBSpecial> findMdlBSpecialList(QueryRequest request, MdlBSpecial mdlBSpecial);

        void createMdlBSpecial(MdlBSpecial mdlBSpecial);

        void updateMdlBSpecial(MdlBSpecial mdlBSpecial);

        void deleteMdlBSpecials(String[]Ids);


        }
