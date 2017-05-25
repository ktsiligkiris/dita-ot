/*
 * This file is part of the DITA Open Toolkit project.
 *
 * Copyright 2010 IBM Corporation
 *
 * See the accompanying LICENSE file for applicable license.

 */
package org.dita.dost.module;

import java.io.File;
import java.util.Collection;
import java.util.function.Predicate;

import org.dita.dost.exception.DITAOTException;
import org.dita.dost.pipeline.AbstractPipelineInput;
import org.dita.dost.pipeline.AbstractPipelineOutput;
import org.dita.dost.util.Job.FileInfo;
import org.dita.dost.writer.CoderefResolver;
/**
 * Coderef Module class.
 *
 * @deprecated since 2.3
 */
@Deprecated
final class CoderefModule extends AbstractPipelineModuleImpl {

    /**
     * Constructor.
     */
    public CoderefModule() {
        super();
    }

    /**
     * Entry point of Coderef Module.
     * @param input Input parameters and resources.
     * @return null
     * @throws DITAOTException exception
     */
    @Override
    public AbstractPipelineOutput execute(final AbstractPipelineInput input)
            throws DITAOTException {
        final Collection<FileInfo> fis = job.getFileInfo(new Predicate<FileInfo>() {
            @Override
            public boolean test(final FileInfo f) {
                return f.hasCoderef;
            }
        });
        if (!fis.isEmpty()) {
            final CoderefResolver writer = new CoderefResolver();
            writer.setJob(job);
            writer.setLogger(logger);
            for (final FileInfo fi: fis) {
                final File f = new File(job.tempDir, fi.file.getPath());
                logger.info("Processing " + f.getAbsolutePath());
                writer.write(f);
            }
        }
        return null;
    }

}
