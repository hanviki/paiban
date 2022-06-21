package cc.mrbird.febs.mdl.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.mdl.entity.MdlDOperation;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 操作目录 服务类
 * </p>
 *
 * @author viki
 * @since 2022-06-09
 */
public interface IMdlDOperationService extends IService<MdlDOperation> {

        IPage<MdlDOperation> findMdlDOperations(QueryRequest request, MdlDOperation mdlDOperation);

        IPage<MdlDOperation> findMdlDOperationList(QueryRequest request, MdlDOperation mdlDOperation);

        void createMdlDOperation(MdlDOperation mdlDOperation) throws FebsException;

        void updateMdlDOperation(MdlDOperation mdlDOperation);

        void deleteMdlDOperations(String[]Ids);


        }
